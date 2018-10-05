

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControlReglasYTerminales {

    public static final String epsilon = "vacio";
    private List<Reglas> reglas;
    private List<String> conjuntoPrimero;
    private List<String> siguientesEnProceso;

    public ControlReglasYTerminales(List<Reglas> producciones) {

        super();
        this.reglas = producciones;
        this.conjuntoPrimero = new ArrayList<String>();
    }

    public Set<String> getTerminales() {

    	//
        Set<String> produccionesderecha = new HashSet<String>();
        for (Reglas prod : getProducciones()) {
            for (String variable : prod.getDerecha()) {
                if (esTerminal(variable)) {
                    produccionesderecha.add(variable);
                }
            }
        }
        return produccionesderecha;
    }

    public Set<String> getNoTerminales() {

        Set<String> reglasnoterminales = new HashSet<String>();
        for (Reglas prod : getProducciones()) {
            if (!esTerminal(prod.getIzquierda())) {
                reglasnoterminales.add(prod.getIzquierda());
            }
        }
        return reglasnoterminales;
    }
    public boolean esTerminal(String variable) {

        for (Reglas prod : getProducciones()) {
            if (prod.getIzquierda().contains(variable)) {
                return false;
            }

        }
        return true;

    }
    public Set<String> getPrimero(String variable) {

        // Se ponen todas las producciones como no procesadas
        for (Reglas prod : getProducciones()) {
            prod.setProcesado(false);
        }
        Set<String> temp = new HashSet<String>();
        List<String> aRetornar = primeropalabra(variable, new ArrayList<String>());
        int contadorVacio = 0;
        int contadorNoVacio = 0;
        // Si existe la misma cantidad de variables que de vacio, significa que
        // cada una de ellas aporto vacio, por lo tanto hay que agregar vacio al
        // conjunto primero una vez esto es un poco tricky pero basicamente sirve para la primera regla
        for (String term : aRetornar) {
            if (term.equals(epsilon)) {
                contadorVacio++;
            } else {
                contadorNoVacio++;
            }
        }
        temp.addAll(aRetornar);
        // Si hay diferentes cantidades de vacio y de variables, significa que
        // no todas aportaron vacio y vacio debe eliminarse
        if (contadorVacio != contadorNoVacio) {
            temp.remove(epsilon);
        }
        return temp;
    }

    public Set<String> getPrimeroProduccion(Reglas variable) {

    	//este es casi lo mismo 
        for (Reglas prod : getProducciones()) {
            prod.setProcesado(false);
        }
        Set<String> temp = new HashSet<String>();
        List<String> aRetornar = primero(variable, new ArrayList<String>());
        int contadorVacio = 0;
        int contadorNoVacio = 0;
        for (String term : aRetornar) {
            if (term.equals(epsilon)) {
                contadorVacio++;
            } else {
                contadorNoVacio++;
            }
        }
        temp.addAll(aRetornar);
        if (contadorVacio != contadorNoVacio) {

            temp.remove(epsilon);

        }

        return temp;
    }
    
    
    
    public List<String> primero(Reglas produccion, List<String> cPrimero) {

        // Si es un terminal se agrega al conjunto primero
        if (esTerminal(produccion.getDerecha().get(0))) {
            cPrimero.add(produccion.getDerecha().get(0));

        }

        for (Reglas prod : getProducciones()) {
            // se pregunta si ya no se proceso para evitar duplicados
            if (prod.equals(produccion)) {

                // la primera variable del lado derecho siempre estara en el
                // conjunto primero
                prod.setProcesado(true);

                List<String> temp = primeropalabra(prod.getDerecha().get(0),
                        new ArrayList<String>());
                cPrimero.addAll(temp);
                for (int i = 0; i < prod.getDerecha().size() - 1; i++) {
                    // si la primera variable deriva en vacio, entonces hay
                    // que agregar el conjunto primero de la siguiente, y
                    // asi sucesivamente
                    if (temp.contains(epsilon)) {
                        primeropalabra(prod.getDerecha().get(i + 1), cPrimero);

                    } else {
                        break;
                    }
                }
            }
        }

        return cPrimero;

    }
    
    

    private List<String> primeropalabra(String variable, List<String> cPrimero) {

        // Si es un terminal se agrega al conjunto primero
        if (esTerminal(variable)) {
            cPrimero.add(variable);

        }

        for (Reglas prod : getProducciones()) {
            // se pregunta si ya no se proceso para evitar duplicados
            if (prod.getIzquierda().equals(variable) && !prod.isProcesado()) {

                // la primera variable del lado derecho siempre estara en el
                // conjunto primero
                prod.setProcesado(true);

                List<String> temp = primeropalabra(prod.getDerecha().get(0),
                        new ArrayList<String>());
                cPrimero.addAll(temp);
                for (int i = 0; i < prod.getDerecha().size() - 1; i++) {
                    // si la primera variable deriva en vacio, entonces hay
                    // que agregar el conjunto primero de la siguiente, y
                    // asi sucesivamente
                    if (temp.contains(epsilon)) {
                        primeropalabra(prod.getDerecha().get(i + 1), cPrimero);

                    } else {
                        break;
                    }
                }
            }
        }

        return cPrimero;

    }

   

    public List<Reglas> getProducciones() {

        return reglas;
    }

    public void setProducciones(List<Reglas> producciones) {

        this.reglas = producciones;
    }

    public List<String> getConjuntoPrimero() {

        return conjuntoPrimero;
    }

    public void setConjuntoPrimero(List<String> conjuntoPrimero) {

        this.conjuntoPrimero = conjuntoPrimero;
    }

    public Set<String> getSiguiente(Map<String, Set<String>> primeros,
            String variable) {

        siguientesEnProceso = new ArrayList<String>();
        return siguiente(primeros, variable);
    }

    private Set<String> siguiente(Map<String, Set<String>> primeros,
            String variable) {

        int index;
        Set<String> siguiente = new HashSet<String>();
        Reglas raiz = reglas.get(0);

        siguientesEnProceso.add(variable);

        // Se agrega "$" en siguiente(S) si S es el simbolo inicial
        if (variable.equals(raiz.getIzquierda())) {
            siguiente.add("$");
        }

        for (Reglas prod : getProducciones()) {
            if ((index = prod.getDerecha().indexOf(variable)) != -1) {

                // Se agrega "$" en siguiente(S) si S es el simbolo inicial
                // Si variable es el ultimo simbolo del lado derecho del a
                // produccion
                // se incluye al siguiente el siguiente del lado izquierdo
                if (prod.getDerecha().size() == (index + 1)) {
                    // Se calcula el siguiente solo si ya no fue calculado
                    if (!siguientesEnProceso.contains(prod.getIzquierda())) {
                        siguiente.addAll(siguiente(primeros,
                                prod.getIzquierda()));
                    }
                }
                // sino se calcula el primero de lo que este a la derecha de
                // variable
                else {
                    int k;
                    for (k = index + 1; k <= prod.getDerecha().size() - 1; k++) {
                        Set<String> set = primeros
                                .get(prod.getDerecha().get(k));
                        // si es un terminal simplemente se agrega dicho
                        // terminal y se termina con esta produccion
                        if (set == null) {
                            siguiente.add(prod.getDerecha().get(k));
                            break;
                        }
                        // sino se agrega el primero
                        siguiente.addAll(set);
                        // Si no contiene vacio se termina con la produccion,
                        // sino se continua calculando el primero del
                        // siguiente simbolo de la produccion
                        if (!set.contains("vacio")) {
                            break;
                        }
                    }
                    // si el primero contiene vacio, se procede a agregar
                    // tambien el siguiente del lado izquierdo de la produccion
                    if (siguiente.contains("vacio")) {
                        siguiente.remove("vacio");
                        // se verifica si el segundo ya fue calculado
                        if (!siguientesEnProceso.contains(prod.getIzquierda())) {
                            siguiente.addAll(siguiente(primeros,
                                    prod.getIzquierda()));
                        }
                    }
                }

            }
        }
        siguientesEnProceso.remove(variable);
        return siguiente;
    }

}

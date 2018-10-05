package control;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Reglas;
import entity.TerminalesYNoTerminales;

public class TablaCamino {

    private Map<TerminalesYNoTerminales, List<Reglas>> tabla;
    private Map<String, Set<String>> conjuntoPrimero;
    private Map<String, Set<String>> conjuntoSiguiente;
    private List<Reglas> reglas;

    public TablaCamino(Map<String, Set<String>> conjuntoPrimero,
            Map<String, Set<String>> conjuntoSiguiente,
            List<Reglas> producciones) {

        super();
        this.conjuntoPrimero = conjuntoPrimero;
        this.conjuntoSiguiente = conjuntoSiguiente;
        this.reglas = producciones;
        //deben ser una gramatica no ambigua y siempre hacia la izquierda ya que esa es la logica del programa sino, se tira
        this.tabla = new HashMap<TerminalesYNoTerminales, List<Reglas>>();
    }

    public Map<TerminalesYNoTerminales, List<Reglas>> generarTabla() {
    	//basicamente genera la tabla probando cada una de las reglas, en funcion de si son terminales o no terminales
    	//para catalogar las reglas en funcion de si son terminales o no terminales.
        ControlReglasYTerminales aux = new ControlReglasYTerminales(getProducciones());
        boolean tieneVacio = true;

        for (Reglas prod : getProducciones()) {
            Set<String> primero = aux.getPrimeroProduccion(prod);

            for (String variable : primero) {

                if (aux.esTerminal(variable)
                        && !variable.equals(ControlReglasYTerminales.epsilon)) {
                    TerminalesYNoTerminales key = new TerminalesYNoTerminales(prod.getIzquierda(), variable);
                    if (getTabla().get(key) == null) {
                        List<Reglas> prodToAdd = new ArrayList<Reglas>();
                        prodToAdd.add(prod);
                        getTabla().put(key, prodToAdd);
                    } else {
                        getTabla().get(key).add(prod);
                    }
                }
            }

            tieneVacio = true;
            for (String a : prod.getDerecha()) {
                if (!aux.esTerminal(a)
                        && !getConjuntoPrimero().get(a).contains(
                                ControlReglasYTerminales.epsilon)) {
                    tieneVacio = false;
                    break;
                }
                if (a.equals(ControlReglasYTerminales.epsilon)) {
                    tieneVacio = true;
                    break;
                }
                if (aux.esTerminal(a)) {
                    tieneVacio = false;
                    break;
                }

            }
            if (tieneVacio) {
                for (String siguiente : getConjuntoSiguiente().get(
                        prod.getIzquierda())) {
                    if (aux.esTerminal(siguiente)
                            && !siguiente.equals(ControlReglasYTerminales.epsilon)) {
                        TerminalesYNoTerminales key = new TerminalesYNoTerminales(prod.getIzquierda(), siguiente);
                        if (getTabla().get(key) == null) {
                            List<Reglas> prodToAdd = new ArrayList<Reglas>();
                            prodToAdd.add(prod);
                            getTabla().put(key, prodToAdd);
                        } else {
                            getTabla().get(key).add(prod);
                        }
                    }
                }
            }

        }

        return getTabla();

    }

    public Map<TerminalesYNoTerminales, List<Reglas>> getTabla() {

        return tabla;
    }

    public void setTablaASP(Map<TerminalesYNoTerminales, List<Reglas>> tablaASP) {

        this.tabla = tablaASP;
    }

    public Map<String, Set<String>> getConjuntoPrimero() {

        return conjuntoPrimero;
    }

    public void setConjuntoPrimero(Map<String, Set<String>> conjuntoPrimero) {

        this.conjuntoPrimero = conjuntoPrimero;
    }

    public Map<String, Set<String>> getConjuntoSiguiente() {

        return conjuntoSiguiente;
    }

    public void setConjuntoSiguiente(Map<String, Set<String>> conjuntoSiguiente) {

        this.conjuntoSiguiente = conjuntoSiguiente;
    }

    public List<Reglas> getProducciones() {

        return reglas;
    }

    public void setProducciones(List<Reglas> producciones) {

        this.reglas = producciones;
    }

}
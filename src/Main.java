
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;


public class Main {

	private static Scanner teclado;

	public static void main(String[] ar) {

		while (true) {
			List<Reglas> reglas = new ArrayList<Reglas>();
			List<String> derecha = new ArrayList<String>();
			ControlReglasYTerminales helper = null;
			String ladoIzq;
			String separador;
			Queue<String> input = new LinkedList<String>();
			Stack<String> stack = new Stack<String>();

			teclado = new Scanner(System.in);
			System.out.println();
			String entrada;
			teclado = new Scanner(System.in);
			System.out.println("Ingrese el caracter que servira como separador");
			separador = teclado.next();
			System.out.println("Ingrese las reglas, para terminar ingrese: ---");
			entrada = teclado.next();

			while (!entrada.equals("---")) {
				if (!entrada.contains("->")) {
					System.out.println("estructura de la regla invalida");
					teclado.nextLine();
					entrada = teclado.next();
					continue;
				}

				String[] entradas = entrada.split("->", 2);

				if (entradas[1].equals("")) {
					System.out.println("falta el lado derecho");
					teclado.nextLine();
					entrada = teclado.next();
					continue;
				}

				String[] variables = entradas[1].split(separador);
				ladoIzq = (entradas[0]);
				derecha = new ArrayList<String>();

				int k;
				for (k = 0; k < variables.length; k++) {
					if (!variables[k].equals("")) {
						derecha.add(variables[k]);
					}
				}
				reglas.add(new Reglas(ladoIzq, derecha, false));
				teclado.nextLine();
				entrada = teclado.next();
			}
			
			helper = new ControlReglasYTerminales(reglas);
			Set<String> noTerminales = helper.getNoTerminales();
			Set<String> terminales = helper.getTerminales();
			System.out.println("Gramatica ingresada:");
			for (Reglas prod : reglas) {
				System.out.println(prod);
			}
			Map<String, Set<String>> primeros = new HashMap<String, Set<String>>();
			Map<String, Set<String>> siguientes = new HashMap<String, Set<String>>();
			for (String it : noTerminales) {
				helper = new ControlReglasYTerminales(reglas);
				Set<String> temp = helper.getPrimero(it);
				System.out.println("Primero de " + it + temp);
				primeros.put(it, temp);
			}
			for (String it : noTerminales) {
				helper = new ControlReglasYTerminales(reglas);
				Set<String> temp = helper.getSiguiente(primeros, it);
				System.out.println("Siguiente de " + it + temp);
				siguientes.put(it, temp);
			}
			TablaCamino tabla = new TablaCamino(primeros, siguientes, reglas);
			Map<TerminalesYNoTerminales, List<Reglas>> tablaGen = (tabla.generarTabla());

			terminales.remove("vacio");
			terminales.add("$");
			boolean esAmbigua = false;
			for (String noTerm : noTerminales) {
				for (String term : terminales) {
					TerminalesYNoTerminales key = new TerminalesYNoTerminales(noTerm, term);
					if (tablaGen.get(key) == null) {
						if (siguientes.get(noTerm).contains(term)) {
							List<Reglas> pp = new ArrayList<Reglas>();
							pp.add(new Reglas("sinc", null, false));
							tablaGen.put(key, pp);
						} 
					} else {
						if (tablaGen.get(key).size() > 1) {
							esAmbigua = true;
						}

					}

				}

			}

			if (esAmbigua) {
				System.out.println("\nLa gramatica es ambigua!");
			} else {
				System.out.println();
				System.out.println("Ingrese Entrada para testear");
				teclado.nextLine();
				entrada = teclado.next();
				String[] entradas = entrada.split(separador);

				for (int k = 0; k < entradas.length; k++) {
					input.add(entradas[k]);
				}

				stack.add("$");
				stack.add(reglas.get(0).getIzquierda());

				Parser.parser(input, stack, tablaGen, reglas);
			}
			System.out.println();
			System.out.println("¿Desea evaluar otra gramatica? Escriba S/N");
			teclado.nextLine();
			entrada = teclado.next();
			if (!entrada.equals("S")) {
				break;
			}
		}

	}
}

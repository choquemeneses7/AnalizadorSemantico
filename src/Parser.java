
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;


public class Parser {

	public static void parser(Queue<String> input, Stack<String> stack,
			Map<TerminalesYNoTerminales, List<Reglas>> tabla, List<Reglas> producciones) {
		//ESTA ES LA CLASE PRINCIPAL DONDE SE HACE TODA LA MAGIA BASICAMENTE VAMOS ANALIZANDO LAS REGLAS Y EN CASO DE 
		//QUE LA CADENA COINCIDA CON LA REGLA  LA REEMPLAZAN Y LA APILAN EN UNA PILA
		String fromEntrada = input.peek();
		String fromPila = stack.peek();
		ControlReglasYTerminales helper = new ControlReglasYTerminales(producciones);

		while (!fromPila.equals("$")) {
			System.out.print("\nStack:" + stack + "\tInput:" + input);

			TerminalesYNoTerminales key = new TerminalesYNoTerminales(fromPila, fromEntrada);
			if (fromEntrada.equals(fromPila)) {
				fromPila = stack.pop();
				fromEntrada = input.poll();
				fromEntrada = input.peek();
			}

			else if (helper.esTerminal(fromPila)) {
				System.out.println("Error al procesar la entrada.");
				return;
			} else if (tabla.get(key) == null) {
				System.out.println("Error al procesar la entrada.");
				return;
			} else {
				if (tabla.get(key).get(0).getDerecha().equals("sinc")) {
					fromPila = stack.pop();

				} else {
					List<String> prod = tabla.get(key).get(0).getDerecha();
					System.out.print("\tAccion:" + tabla.get(key).get(0));

					fromPila = stack.pop();

					for (int j = prod.size() - 1; j >= 0; j--) {
						if (!prod.get(j).equals("vacio"))
							stack.push(prod.get(j));
					}
				}

			}
			fromPila = stack.peek();
		}
	}

}

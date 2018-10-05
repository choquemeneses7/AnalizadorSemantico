package entity;

import java.util.List;

public class Reglas {
	private String iz;
	private boolean procesado;
	private List<String> der;
	//aqui basicamente estoy definiendo la forma de las reglas donde por ejemplo para 
	//A->i A seria izquierda y i seria derecha y terminales
	//procesado es para verificar si ya ha sido procesada y analizada o no la regla
	public Reglas(String izquierda, List<String> derecha, boolean procesado) {

		super();
		this.iz = izquierda;
		this.der = derecha;
		this.procesado = procesado;
	}

	@Override
	public String toString() {

		return iz + "->" + der;
	}

	public String getIzquierda() {

		return iz;
	}

	public void setIzquierda(String izquierda) {

		this.iz = izquierda;
	}

	public List<String> getDerecha() {

		return der;
	}

	public void setDerecha(List<String> derecha) {

		this.der = derecha;
	}

	public boolean isProcesado() {
		return procesado;
	}

	public void setProcesado(boolean procesado) {
		this.procesado = procesado;
	}

}

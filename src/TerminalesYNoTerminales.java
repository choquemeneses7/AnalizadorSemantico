
public class TerminalesYNoTerminales {

	private String terminal;
	private String noTerminal;

	public TerminalesYNoTerminales(String noTerminal, String terminal) {
		this.noTerminal = noTerminal;
		this.terminal = terminal;

	}

	@Override
	public int hashCode() {
		int hashcode = 1;
		hashcode = 31 + terminal.hashCode();
		hashcode = hashcode * 31 + noTerminal.hashCode();
		return hashcode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TerminalesYNoTerminales other = (TerminalesYNoTerminales) obj;
		if (!terminal.equals(other.terminal))
			return false;
		if (!noTerminal.equals(other.noTerminal))
			return false;
		return true;

	}

	@Override
	public String toString() {
		return "M[" + this.noTerminal + "," + this.terminal + "]";
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

}

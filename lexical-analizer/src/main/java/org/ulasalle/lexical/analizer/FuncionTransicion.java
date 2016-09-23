package lexico;

import java.util.List;;

public class FuncionTransicion {
	
	private int estadoSiguiente;
	
	private char[] caracteresAceptados;
	
	public FuncionTransicion(char[] caracteresAceptados,int estadoSiguiente)
	{
		this.caracteresAceptados=caracteresAceptados;
		this.estadoSiguiente=estadoSiguiente;
	}
	
	
	
	public int getEstadoSiguiente() {
		return estadoSiguiente;
	}



	public char[] getCaracteresAceptados() {
		return caracteresAceptados;
	}



	public boolean aceptar(char letra)
	{
		
		for(int i=0; i<caracteresAceptados.length;i++ )
		{
			if(letra==caracteresAceptados[i])
			{
				return true;
			}
		}
		return false;
	}
	


}

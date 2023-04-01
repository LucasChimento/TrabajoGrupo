package Paquete1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculadora {

	static Scanner entrada;
	static String opcion="";
	static double resultado=0;
	static double[] numeros= new double[2];
	static boolean limpio=true;
	
	public static void main(String[] args)
	{
		while(!opcion.equals("s"))
		{
			entrada= new Scanner(System.in);
			System.out.println(resultado+"\n");
			System.out.println("1)Suma\n2)Resta\n3)Multiplicacion\n4)Division\nL)Limpiar\nS)Salir");
			opcion=entrada.next().toLowerCase();
			menu();
		}
		
		entrada.close();
	}
	public static void menu()
	{
	
		switch(opcion)
		{
		case "1":
			System.out.println("Sumando");
			numeros=solicitarNumeros('+');
			resultado=suma(numeros);
			break;
		case "2":
			System.out.println("Restando");
			numeros=solicitarNumeros('-');
			resultado=resta(numeros);
			break;
		case "3":
			System.out.println("Multiplicando");
			numeros=solicitarNumeros('*');
			resultado=producto(numeros);
			break;
		case "4":
			System.out.println("Dividiendo");
			numeros=solicitarNumeros('/');
			resultado=division(numeros);
			break;
		case "l":
			limpio=true;
			resultado=0;
			break;
		case "s":
			System.out.println("Saliendo...");
			break;
			default:
				System.out.println("Elija una de las opciones disponibles\n");
				break;	
		}
	}
	public static double[] solicitarNumeros(char operacion)
	{		
		double[] nums=new double[2];
		if(limpio)
		{			
			try 
			{
				nums[0]=entrada.nextDouble();
				System.out.println(operacion);
				nums[1]=entrada.nextDouble();
				System.out.println("---------");
				limpio=false;
				return nums;
			}
			catch(InputMismatchException e)
			{
				System.out.println("Syntax error!");
				limpio=true;
				return null;
			}
		}
		else
		{
			try 
			{
				nums[0]=resultado;
				System.out.println(resultado);
				System.out.println(operacion);
				nums[1]=entrada.nextDouble();
				System.out.println("---------");
				return nums;
			}
			catch(InputMismatchException e)
			{
				System.out.println("Syntax error!");
				limpio=true;
				return null;
			}
		}
	}
	public static double suma(double[] numeros)
	{
		if(numeros==null) return 0;
		return numeros[0]+numeros[1];
	}
	public static double resta(double[] numeros)
	{
		if(numeros==null) return 0;
		return numeros[0]-numeros[1];
	}
	public static double producto(double[] numeros)
	{
		if(numeros==null) return 0;
		return numeros[0]*numeros[1];
	}
	public static double division(double[] numeros)
	{
		if(numeros==null) return 0;
		if(numeros[1]==0)
		{
			System.out.println("Error division por 0!");
			limpio=true;
			 return 0;
		}
		return numeros[0]/numeros[1];
	}
}

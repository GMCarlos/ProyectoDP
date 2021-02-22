package Personajes;

import Clases.Mapa;

public class SuperHeroeFisico extends SuperHeroe{

	public SuperHeroeFisico() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SuperHeroeFisico(String nombre, char marca, int turno) {
		super(nombre, marca, turno);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String x= new String();
		int a;
		Mapa mapa= Mapa.obtenerInstancia();
		if(this.getTurno()-1==mapa.getTurno() && getSeHaMovido()==true ) {
			a=getTurno()-1;
			x="(shphysical:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+a+":"+this.getArbolArmas().toString()+")";
		}else {
			x="(shphysical:"+this.getMarca()+":"+this.getNumSalaDondeSeEncuentra()+":"+this.getTurno()+":"+this.getArbolArmas().toString()+")";
		}
		return x;
	}
}	


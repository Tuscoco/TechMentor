package model;

import java.time.LocalTime;

public class CargaHoraria {
    
    private int id_monitor;
	private int id_materia;
	private String dia_semana;
	private LocalTime horario_e;
	private LocalTime horario_s;


	public CargaHoraria(int id_monitor, int id_materia, String dia_semana, LocalTime horario_e, LocalTime horario_s) {

		this.id_monitor = id_monitor;
		this.id_materia = id_materia;
		this.dia_semana = dia_semana;
		this.horario_e = horario_e;
		this.horario_s = horario_s;

	}


	public int getIdMonitor() {
		return id_monitor;
	}


	public int getIdMateria() {
		return id_materia;
	}


	public String getDia() {
		return dia_semana;
	}


	public LocalTime getHorarioE() {
		return horario_e;
	}


	public void setHorarioE(LocalTime horario) {
		this.horario_e = horario;
	}


	public LocalTime getHorarioS() {
		return horario_s;
	}


	public void setHorarioS(LocalTime horario) {
		this.horario_s = horario;
	}
    
}

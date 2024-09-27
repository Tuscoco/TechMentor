package model;

public class Carga_horaria {
    
    private String dia_da_semana;
    private int matricula;
    private int cod_materia;
    private String horario_entrada;
    private String horario_saida;
    
    
	public Carga_horaria(String dia_da_semana, int matricula, int cod_materia, String horario_entrada, String horario_saida) {
		
		this.dia_da_semana = dia_da_semana;
		this.matricula = matricula;
		this.cod_materia = cod_materia;
		this.horario_entrada = horario_entrada;
		this.horario_saida = horario_saida;
		
	}


	public String getDia_da_semana() {
		return dia_da_semana;
	}


	public int getMatricula() {
		return matricula;
	}


	public int getCod_materia() {
		return cod_materia;
	}


	public String getHorario_entrada() {
		return horario_entrada;
	}


	public String getHorario_saida() {
		return horario_saida;
	}
    
}

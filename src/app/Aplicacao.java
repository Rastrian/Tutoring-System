package app;

import java.io.IOException;

import business.EditalMonitoria;
import dao.Dao;
import dao.EditalMonitoriaDao;

public class Aplicacao {

	public static void main(String[] args) throws IOException {
		
		//Etapa 1
		Dao<EditalMonitoria, String> editalMonitoriaDao = new EditalMonitoriaDao("editaismonitoria.bin");
		
		//Cria as editais e salva
		editalMonitoriaDao.add(new EditalMonitoria("Edital Prog. Modular Noite", "Prog. Modular", "Noite", 1.5));
		editalMonitoriaDao.add(new EditalMonitoria("Edital AEDS Noite", "AEDS", "Noite", 2));
		
		//Busca um edital pelo nome
		EditalMonitoria teste = editalMonitoriaDao.get("Edital Prog. Modular Noite");
		System.out.println(teste.getTurno());
	}

}

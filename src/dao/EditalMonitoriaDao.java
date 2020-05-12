package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import business.EditalMonitoria;

public class EditalMonitoriaDao implements Dao<EditalMonitoria, String>{
	
	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;
	
	public EditalMonitoriaDao(String fileName) throws IOException {
		file = new File(fileName);
		fos = new FileOutputStream(file, false); 
		outputFile = new ObjectOutputStream(fos);
	}
	
	//Adiciona edital
	public void add(EditalMonitoria edtialMonitoria) {
		try {
			outputFile.writeObject(edtialMonitoria);
		} catch (Exception e) {
			System.out.println("Erro ao gravar o edital '" + edtialMonitoria.getNome() + "' no disco!");
			e.printStackTrace();
		}
	}
	
	//Get edital
	public EditalMonitoria get(String chave) {
		EditalMonitoria editalmonitoria = null;
		
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				editalmonitoria = (EditalMonitoria) inputFile.readObject();

				if (chave.equals(editalmonitoria.getNome())) {
					return editalmonitoria;
				}
			}
		}catch(Exception e) {
			System.out.println("ERRO ao ler o produto '" + chave + "' do disco!");
			e.printStackTrace();
		}
		return null;
	}
	
	public void update(EditalMonitoria edtialMonitoria) {
		
	}
	
	public void remove(EditalMonitoria edtialMonitoria) {
		
	}
	
	public List<EditalMonitoria> getAll(){
		return null;
	}
}

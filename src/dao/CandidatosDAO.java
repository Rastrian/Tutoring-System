package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import profiles.Candidatos;

public class CandidatosDAO implements DAO<Candidatos, Integer> {
	private String filename = "Candidatos.bin";
	private File file = new File(filename);
	private static List<Candidatos> lista;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public CandidatosDAO() throws IOException {
		if (!file.exists()) {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);
		}
		lista = new ArrayList<Candidatos>();
		readFromFile();
	}

	@Override
	public Candidatos get(Integer id) {
		readFromFile();
		for (Candidatos usu : lista) {
			if (usu.getId().equals(id)) {
				return usu;
			}
		}
		return null;
	}

	@Override
	public boolean add(Candidatos Candidatos) {
		try { 
			if(!lista.contains(Candidatos)) {
				lista.add(Candidatos);
			}else{
				return false;
			}
			saveToFile();
			return true;
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Candidatos '" + Candidatos.getId() + "' no disco!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(Candidatos Candidatos) {
		int index = lista.indexOf(Candidatos);
		if (index != -1) {
			lista.set(index, Candidatos);
		}
		saveToFile();
	}

	@Override
	public void remove(Candidatos Candidatos) {
		int index = lista.indexOf(Candidatos);
		if (index != -1) {
			lista.remove(index);
		}
		saveToFile();

	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Candidatos Candidatos : lista) {
				outputFile.writeObject(Candidatos);
			}
			outputFile.flush();
			readFromFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Candidatos no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Candidatos> getAll() {
		return lista;
	}

	private List<Candidatos> readFromFile() {
		lista = new ArrayList<Candidatos>();
		Candidatos Candidatos = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				Candidatos = (Candidatos) inputFile.readObject();
				lista.add(Candidatos);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Candidatos no disco!");
			e.printStackTrace();
		}
		return lista;
	}

	public int count() {
		return readFromFile().size();
	}

	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}

	@Override
	protected void finalize() throws Throwable {
		this.close();
	}

}
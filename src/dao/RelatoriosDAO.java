package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import profiles.Relatorios;

public class RelatoriosDAO implements DAO<Relatorios, Integer> {
	private String filename = "Relatorios.bin";
	private File file = new File(filename);
	private static List<Relatorios> lista;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public RelatoriosDAO() throws IOException {
		if (!file.exists()) {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);
		}
		lista = new ArrayList<Relatorios>();
		readFromFile();
	}

	@Override
	public Relatorios get(Integer id) {
		readFromFile();
		for (Relatorios usu : lista) {
			if (usu.getId().equals(id)) {
				return usu;
			}
		}
		return null;
	}

	@Override
	public boolean add(Relatorios Relatorios) {
		try { 
			if(!lista.contains(Relatorios)) {
				lista.add(Relatorios);
			}else{
				return false;
			}
			saveToFile();
			return true;
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Relatorios '" + Relatorios.getId() + "' no disco!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(Relatorios Relatorios) {
		int index = lista.indexOf(Relatorios);
		if (index != -1) {
			lista.set(index, Relatorios);
		}
		saveToFile();
	}

	@Override
	public void remove(Relatorios Relatorios) {
		int index = lista.indexOf(Relatorios);
		if (index != -1) {
			lista.remove(index);
		}
		saveToFile();

	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Relatorios Relatorios : lista) {
				outputFile.writeObject(Relatorios);
			}
			outputFile.flush();
			readFromFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Relatorios no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Relatorios> getAll() {
		return lista;
	}

	private List<Relatorios> readFromFile() {
		lista = new ArrayList<Relatorios>();
		Relatorios Relatorios = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				Relatorios = (Relatorios) inputFile.readObject();
				lista.add(Relatorios);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Relatorios no disco!");
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
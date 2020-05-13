package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import profiles.Vagas;

public class VagasDAO implements DAO<Vagas, Integer> {
	private String filename = "Vagas.bin";
	private File file = new File(filename);
	private static List<Vagas> lista;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public VagasDAO() throws IOException {
		if (!file.exists()) {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);
		}
		lista = new ArrayList<Vagas>();
		readFromFile();
	}

	@Override
	public Vagas get(Integer id) {
		readFromFile();
		for (Vagas usu : lista) {
			if (usu.getId().equals(id)) {
				return usu;
			}
		}
		return null;
	}

	@Override
	public boolean add(Vagas Vagas) {
		try { 
			if(!lista.contains(Vagas)) {
				lista.add(Vagas);
			}else{
				return false;
			}
			saveToFile();
			return true;
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Vagas '" + Vagas.getId() + "' no disco!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(Vagas Vagas) {
		int index = lista.indexOf(Vagas);
		if (index != -1) {
			lista.set(index, Vagas);
		}
		saveToFile();
	}

	@Override
	public void remove(Vagas Vagas) {
		int index = lista.indexOf(Vagas);
		if (index != -1) {
			lista.remove(index);
		}
		saveToFile();

	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Vagas Vagas : lista) {
				outputFile.writeObject(Vagas);
			}
			outputFile.flush();
			readFromFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Vagas no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Vagas> getAll() {
		return lista;
	}

	private List<Vagas> readFromFile() {
		lista = new ArrayList<Vagas>();
		Vagas Vagas = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				Vagas = (Vagas) inputFile.readObject();
				lista.add(Vagas);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Vagas no disco!");
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
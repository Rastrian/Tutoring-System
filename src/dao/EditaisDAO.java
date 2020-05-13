package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import profiles.Editais;

public class EditaisDAO implements DAO<Editais, Integer> {
	private String filename = "Editais.bin";
	private File file = new File(filename);
	private static List<Editais> lista;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public EditaisDAO() throws IOException {
		if (!file.exists()) {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);
		}
		lista = new ArrayList<Editais>();
		readFromFile();
	}

	@Override
	public Editais get(Integer id) {
		readFromFile();
		for (Editais usu : lista) {
			if (usu.getId().equals(id)) {
				return usu;
			}
		}
		return null;
	}

	@Override
	public boolean add(Editais Editais) {
		try { 
			if(!lista.contains(Editais)) {
				lista.add(Editais);
			}else{
				return false;
			}
			saveToFile();
			return true;
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Editais '" + Editais.getId() + "' no disco!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(Editais Editais) {
		int index = lista.indexOf(Editais);
		if (index != -1) {
			lista.set(index, Editais);
		}
		saveToFile();
	}

	@Override
	public void remove(Editais Editais) {
		int index = lista.indexOf(Editais);
		if (index != -1) {
			lista.remove(index);
		}
		saveToFile();

	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Editais Editais : lista) {
				outputFile.writeObject(Editais);
			}
			outputFile.flush();
			readFromFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Editais no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Editais> getAll() {
		return lista;
	}

	private List<Editais> readFromFile() {
		lista = new ArrayList<Editais>();
		Editais Editais = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				Editais = (Editais) inputFile.readObject();
				lista.add(Editais);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar Editais no disco!");
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
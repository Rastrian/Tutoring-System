package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import profiles.Usuario;

public class UsuarioDAO implements DAO<Usuario, Integer> {
	private String filename = "Usuarios.bin";
	private File file = new File(filename);
	private static List<Usuario> usuarios;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public UsuarioDAO() throws IOException {
		if (!file.exists()) {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);
		}
		usuarios = new ArrayList<Usuario>();
		readFromFile();
	}

	@Override
	public Usuario get(Integer id) {
		readFromFile();
		for (Usuario usu : usuarios) {
			if (usu.getId().equals(id)) {
				return usu;
			}
		}
		return null;
	}

	@Override
	public boolean add(Usuario usuario) {
		try { 
			if(!usuarios.contains(usuario)) {
				usuarios.add(usuario);
			}else{
				return false;
			}
			saveToFile();
			return true;
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o usuario '" + usuario.getId() + "' no disco!");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void update(Usuario usuario) {
		int index = usuarios.indexOf(usuario);
		if (index != -1) {
			usuarios.set(index, usuario);
		}
		saveToFile();
	}

	@Override
	public void remove(Usuario usuario) {
		int index = usuarios.indexOf(usuario);
		if (index != -1) {
			usuarios.remove(index);
		}
		saveToFile();

	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Usuario usuario : usuarios) {
				outputFile.writeObject(usuario);
			}
			outputFile.flush();
			readFromFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar usuario no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Usuario> getAll() {
		return usuarios;
	}

	private List<Usuario> readFromFile() {
		usuarios = new ArrayList<Usuario>();
		Usuario usuario = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {
			while (fis.available() > 0) {
				usuario = (Usuario) inputFile.readObject();
				usuarios.add(usuario);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar usuario no disco!");
			e.printStackTrace();
		}
		return usuarios;
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
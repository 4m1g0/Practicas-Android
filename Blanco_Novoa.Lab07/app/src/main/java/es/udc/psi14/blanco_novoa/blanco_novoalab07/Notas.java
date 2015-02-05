package es.udc.psi14.blanco_novoa.blanco_novoalab07;

/**
 * Created by 4m1g0 on 4/11/14.
 */
public class Notas {
    private int id, nota;
    private String nombre, apellido, materia, mencion;

    public Notas(int id, int nota, String nombre, String apellido, String materia, String mencion) {
        this.id = id;
        this.nota = nota;
        this.nombre = nombre;
        this.apellido = apellido;
        this.materia = materia;
        this.mencion = mencion;
    }

    public Notas() {
    }

    public int getId() {
        return id;
    }

    public int getNota() {
        return nota;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setMencion(String mencion) {
        this.mencion = mencion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMateria() {
        return materia;
    }

    public String getMencion() {
        return mencion;
    }
}

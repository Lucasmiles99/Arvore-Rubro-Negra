package Pacote;

import java.util.concurrent.ThreadLocalRandom;

public class Reserva {
    int Id;
    String cpf;
    String nomeCliente;
    int numeroQuarto;
    int dataInicio;
    int dataFim;
    String dataCheckIn;
    String dataCheckOut;
    String categoria;

    public Reserva(String cpf, String nomeCliente, int numeroQuarto, String dataCheckIn, String dataCheckOut, String categoria) {
    	this.Id = ThreadLocalRandom.current().nextInt(1, 1000000); // Gera ID único
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.numeroQuarto = numeroQuarto;
        this.dataInicio = Integer.parseInt(dataCheckIn.replace("-", "")); // Converter datas em formato numérico
        this.dataFim = Integer.parseInt(dataCheckOut.replace("-", ""));
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.categoria = categoria;
    }
    
    public int getNumeroQuarto() {
        return numeroQuarto;
    }
    
    public int getId() {
    	return Id;
    }

    public boolean conflitaCom(String dataInicio, String dataFim) {
        int dataInicioInt = Integer.parseInt(dataInicio.replace("-", ""));
        int dataFimInt = Integer.parseInt(dataFim.replace("-", ""));

        return !(this.dataFim < dataInicioInt || this.dataInicio > dataFimInt);
    }

    public boolean mesmaCategoria(String categoria) {
        return this.categoria.equalsIgnoreCase(categoria);
    }

    @Override
    public String toString() {
        return "Reserva{" +
        		"ID =' " + Id + '\'' +
                "CPF =' " + cpf + '\'' +
                ", Nome =' " + nomeCliente + '\'' +
                ", Quarto = " + numeroQuarto +
                ", Check-in =' " + dataCheckIn + '\'' +
                ", Check-out =' " + dataCheckOut + '\'' +
                ", Categoria =' " + categoria + '\'' +
                '}';
    }
}
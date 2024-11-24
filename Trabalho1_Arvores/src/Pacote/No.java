package Pacote;

class No {
    Reserva reserva;
    No left, right, parent;
    boolean isRed;
    boolean color;

    public No(Reserva reserva) {
        this.reserva = reserva;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.isRed = true; // Todo novo nó é inicialmente vermelho
        this.color = true;
    }
}
import java.util.ArrayList;
import java.util.List;

public class Message {

    private final boolean isAttack;

    private final List<Integer> chain;

    public Message(List<Integer> chain, boolean isAttack) {
        this.isAttack = isAttack;
        this.chain = chain;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public List<Integer> getChain() {
        return chain;
    }

    public static Message newHandMessage(Message message, int generalNumber) {
        return newHandValueMessage(message, generalNumber, message.isAttack());
    }

    public static Message newHandValueMessage(Message message, int generalNumber, boolean isAttack) {
        List<Integer> newChain = new ArrayList<>(message.getChain());
        newChain.add(generalNumber);
        return new Message(newChain, isAttack);
    }
}

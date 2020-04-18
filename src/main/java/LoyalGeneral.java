import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LoyalGeneral implements Messenger, General {

    private final EIG<Boolean> eigTree;

    private final List<Messenger> otherGeneralsMessengers;

    private final int generalsCount;

    private final int generalNumber;

    private final boolean proposedValue;

    private List<Message> messages = new ArrayList<>();

    private ConsensusListener consensusListener;

    public LoyalGeneral(
            int generalNumber,
            int generalsCount,
            List<Messenger> messengers,
            boolean proposedValue,
            ConsensusListener consensusListener
    ) {
        this.eigTree = new EIG<>(proposedValue);
        this.otherGeneralsMessengers = messengers;
        this.generalsCount = generalsCount;
        this.generalNumber = generalNumber;
        this.proposedValue = proposedValue;
        this.consensusListener = consensusListener;
    }

    @Override
    public void sendMessage(Message message) {
        messages.add(message);
        Message newMessage = Message.newHandMessage(message, generalNumber);
        if (newMessage.getChain().size() <= generalsCount && !message.getChain().contains(generalNumber)) {
            handleMessage(newMessage);
        }
    }

    @Override
    public void startConsensus() {
        Message message = new Message(Collections.singletonList(generalNumber), proposedValue);
        handleMessage(message);
    }

    @Override
    public void startComputation() {
        messages.sort(Comparator.comparingInt(message -> message.getChain().size()));
        messages.forEach(message -> eigTree.addNewNode(new EIG.LabeledValue<>(message.getChain(), message.isAttack())));
        consensusListener.onConsensus(generalNumber, eigTree.computeChildMajority());
    }

    private void handleMessage(Message message) {
        messages.add(message);
        notifyAllGeneralsWithMessage(message);
    }

    private void notifyAllGeneralsWithMessage(Message message) {
        otherGeneralsMessengers.forEach(messenger -> messenger.sendMessage(message));
    }

}

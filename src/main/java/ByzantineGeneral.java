import kotlin.random.Random;

import java.util.Collections;
import java.util.List;

public class ByzantineGeneral implements Messenger, General {

    private final List<Messenger> otherGeneralsMessengers;

    private final int generalNumber;

    private final int generalsCount;

    public ByzantineGeneral(
            int generalNumber,
            List<Messenger> messengers,
            int generalsCount
    ) {
        this.otherGeneralsMessengers = messengers;
        this.generalNumber = generalNumber;
        this.generalsCount = generalsCount;
    }

    @Override
    public void sendMessage(Message message) {
        if (message.getChain().size() + 1 <= generalsCount && !message.getChain().contains(generalNumber)) {
            otherGeneralsMessengers.forEach(messenger -> messenger.sendMessage(gerRandomMessage(message)));
        }
    }

    @Override
    public void startConsensus() {
        otherGeneralsMessengers.forEach(messenger -> messenger.sendMessage(
                new Message(
                        Collections.singletonList(generalNumber), random())
                )
        );
    }

    @Override
    public void startComputation() {
        //nothing to do
    }

    private Message gerRandomMessage(Message message) {
        return Message.newHandValueMessage(
                        message,
                        generalNumber,
                        random()
        );
    }

    private boolean random() {
        return Random.Default.nextInt(0, 100) > 50;
    }
}

import java.util.ArrayList;
import java.util.List;

public class ByzantineProblemEmulator {

    private static final int COUNT = 4;

    private static Messenger logMessenger = message -> {
        System.out.println("Message");
        System.out.print("Chain: ");
        System.out.print(message.getChain().get(0));
        if (message.getChain().size() > 1) {
            message.getChain()
                    .subList(1, message.getChain().size())
                    .forEach(generalNumber -> {
                        System.out.print(" -> ");
                        System.out.print(generalNumber);
                    });
        }
        System.out.println();
        System.out.print("Value: ");
        System.out.println(message.isAttack());
        System.out.println("*************************************************");
    };

    private static ConsensusListener logConsensusListener = (generalNumber, consensusValue) -> {
        System.out.println();
        System.out.print("Decided value for ");
        System.out.print(generalNumber);
        System.out.print(" is ");
        System.out.print(consensusValue);
    };

    public void emulate() {
        List<Messenger> messengers1 = new ArrayList<>();
        List<Messenger> messengers2 = new ArrayList<>();
        List<Messenger> messengers3 = new ArrayList<>();
        List<Messenger> messengers4 = new ArrayList<>();
        messengers1.add(logMessenger);
        messengers2.add(logMessenger);
        messengers3.add(logMessenger);
        messengers4.add(logMessenger);
        LoyalGeneral loyalGeneral1 = new LoyalGeneral(0, COUNT, messengers1, true, logConsensusListener);
        LoyalGeneral loyalGeneral2 = new LoyalGeneral(1,COUNT, messengers2, true, logConsensusListener);
        LoyalGeneral loyalGeneral3 = new LoyalGeneral(2, COUNT, messengers3, true, logConsensusListener);
        ByzantineGeneral byzantineGeneral4 = new ByzantineGeneral(3, messengers4, COUNT);
        messengers1.add(loyalGeneral2);
        messengers1.add(loyalGeneral3);
        messengers1.add(byzantineGeneral4);
        messengers2.add(loyalGeneral1);
        messengers2.add(loyalGeneral3);
        messengers2.add(byzantineGeneral4);
        messengers3.add(loyalGeneral2);
        messengers3.add(loyalGeneral1);
        messengers3.add(byzantineGeneral4);
        messengers4.add(loyalGeneral1);
        messengers4.add(loyalGeneral2);
        messengers4.add(loyalGeneral3);
        loyalGeneral1.startConsensus();
        loyalGeneral2.startConsensus();
        loyalGeneral3.startConsensus();
        byzantineGeneral4.startConsensus();

        loyalGeneral1.startComputation();
        loyalGeneral2.startComputation();
        loyalGeneral3.startComputation();
    }

}

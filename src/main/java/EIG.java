import java.util.*;

public class EIG<T> {

    private T value;

    private List<Integer> label = Collections.emptyList();

    private List<EIG<T>> childes;

    public EIG(T initialValue) {
        this.value = initialValue;
    }

    private EIG(List<Integer> label, T value) {
        this.label = label;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void addNewNode(LabeledValue<T> value) {
        int valueLabelSize = value.getLabel().size();

        if (valueLabelSize - 1 == label.size()) {
            addValueToChildes(value);
        } else if (childes != null) {
            for (EIG<T> child : childes) {
                int childLabelSize = child.getLabel().size();
                if (child.getLabel().get(childLabelSize - 1).equals(value.getLabel().get(childLabelSize - 1))) {
                    child.addNewNode(value);
                }
            }
        }
    }

    private void addValueToChildes(LabeledValue<T> value) {
        if (childes == null) {
            childes = new ArrayList<>();
        }
        childes.add(fromLabeledValue(value));
    }

    public T computeChildMajority() {
        if (childes == null) {
            return value;
        }
        Map<T, Long> countMap = new HashMap<>();
        long majorityCount = 0;
        T majorityValue = null;
        for (EIG<T> child : childes) {
            T childValue = child.computeChildMajority();
            if (!countMap.containsKey(childValue)) {
                long count = childes
                        .stream()
                        .filter(node -> node.value != null && node.computeChildMajority().equals(childValue))
                        .count();
                countMap.put(childValue, count);
                if (count >= majorityCount) {
                    majorityValue = childValue;
                    majorityCount = count;
                }
            }
        }
        return majorityValue;
    }

    private List<Integer> getLabel() {
        return label;
    }

    private static <T> EIG<T> fromLabeledValue(LabeledValue<T> labeledValue) {
        return new EIG<>(labeledValue.getLabel(), labeledValue.getValue());
    }

    public static class LabeledValue<T> {

        private final List<Integer> label;
        private final T value;

        public LabeledValue(List<Integer> label, T value) {
            this.label = label;
            this.value = value;
        }

        public List<Integer> getLabel() {
            return label;
        }

        public T getValue() {
            return value;
        }
    }

}

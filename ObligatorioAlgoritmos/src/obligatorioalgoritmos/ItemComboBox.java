package obligatorioalgoritmos;

public class ItemComboBox {
    
    private String key;
    private int value;

    public ItemComboBox(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
    
}

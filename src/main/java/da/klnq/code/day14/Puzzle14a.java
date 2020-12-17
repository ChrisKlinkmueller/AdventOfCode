package da.klnq.code.day14;

public class Puzzle14a extends Program {

    public static void main(String[] args) {
        Program program = new Puzzle14a();
        program.execute();
    }

    @Override
    protected void storeValue(long address, long value) {
        String bitMap = this.toBitMap(value);

        final StringBuilder maskedBitMap = new StringBuilder();
        for (int i = 0; i < this.mask.length(); i++) {
            maskedBitMap.append(this.mask.charAt(i) == 'X'
                ? bitMap.charAt(i) 
                : this.mask.charAt(i)
            );
        }

        final long maskedValue = this.fromBitMap(maskedBitMap.toString());
        this.memory.put(address, maskedValue);
    }

}
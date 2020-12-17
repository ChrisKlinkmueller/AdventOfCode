package da.klnq.code.day14;

public class Puzzle14b extends Program {

    public static void main(String[] args) {
        Program program = new Puzzle14b();
        program.execute();
    }

    @Override
    protected void storeValue(long address, long value) {
        String bitMap = this.toBitMap(address);
        bitMap = this.applyMask(bitMap);
        this.storeValue(bitMap, value);
    }

    private String applyMask(String address) {
        final StringBuilder maskedAddress = new StringBuilder();
        for (int i = 0; i < this.mask.length(); i++) {
            if (this.mask.charAt(i) == '1') {
                maskedAddress.append('1');
            }
            else if (this.mask.charAt(i) == '0') {
                maskedAddress.append(address.charAt(i));
            }
            else if (this.mask.charAt(i) == 'X') {
                maskedAddress.append('X');
            }
        }
        return maskedAddress.toString();
    }

    private void storeValue(String bitMap, long value) {        
        final int index = bitMap.indexOf("X");
        if (index < 0) {
            final long address = this.fromBitMap(bitMap);
            this.memory.put(address, value);
        }
        else {
            this.storeValue(bitMap.replaceFirst("X", "0"), value);
            this.storeValue(bitMap.replaceFirst("X", "1"), value);
        }
    }
    
}

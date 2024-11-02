public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Original array, indexed from 0
        int[] arrayZeroIndexed = {0, 5, 3, 3, 0};

        // Create a new array, one element larger
        int[] arrayOneIndexed = new int[arrayZeroIndexed.length + 1];

        // Copy elements from the original array to the new array, starting at index 1
        for (int i = 0; i < arrayZeroIndexed.length; i++) {
            arrayOneIndexed[i + 1] = arrayZeroIndexed[i];
        }

        // Print the new array to verify
        for (int i = 1; i < arrayOneIndexed.length; i++) {
            System.out.println("Index " + i + ": " + arrayOneIndexed[i]);
        }


    }
}
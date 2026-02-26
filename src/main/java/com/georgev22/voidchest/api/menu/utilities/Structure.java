package com.georgev22.voidchest.api.menu.utilities;

import java.util.Arrays;

public class Structure {
    private final String[] structure;

    /**
     * Creates a new structure for a menu layout.
     *
     * @param structure An array of strings, each string representing a row.
     *                  Each row must be at most 9 slots (ignoring spaces).
     */
    public Structure(String... structure) {
        if (structure == null || structure.length == 0) {
            throw new IllegalArgumentException("Structure cannot be null or empty");
        }

        String[] newStructure = new String[structure.length];
        for (int i = 0; i < structure.length; i++) {
            String row = structure[i];
            if (row == null) {
                throw new IllegalArgumentException("Row " + i + " cannot be null");
            }

            String newRow = row.replace(" ", "");
            int length = newRow.length();
            if (length > 9) {
                throw new IllegalArgumentException("Row '" + row + "' is too long (" + length + " > 9)");
            }

            newStructure[i] = newRow;
        }

        this.structure = newStructure;
    }

    /**
     * Returns the raw structure rows.
     *
     * @return The structure rows as an array of strings.
     */
    public String[] getStructure() {
        return Arrays.copyOf(structure, structure.length);
    }

    /**
     * Returns the number of rows in this structure.
     *
     * @return Number of rows.
     */
    public int getRows() {
        return structure.length;
    }
}

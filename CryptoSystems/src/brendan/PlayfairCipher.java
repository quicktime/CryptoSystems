package brendan;

/**
 * <p>This class implements a version of the Playfair cipher: 
 * <dl>
 *   <dt>Omitted letter</dt>
 *     <dd>The omitted letter is "Q": replace every "QU" with "KW", and 
 *         every lone "Q" with "K", before encrypting the message.</dd>
 *   <dt>Grid filling</dt>
 *     <dd>When filling the grid from the key, fill in the top row from
 *         left to right, then the second row from left to right, and 
 *         similarly the third, fourth, and fifth rows.</dd>
 *   <dt>Double letters</dt>
 *     <dd>If double letters need splitting when encrypting, split them
 *         with an 'X'.</dd>
 *   <dt>Double 'X's</dt>
 *     <dd>If the message contains two 'X's that need to be split, split
 *         them with a 'Z'.</dd> 
 *   <dt>Single last letter</dt>
 *     <dd>If there is a lone last letter when encrypting, append an 
 *         'X'.</dd>
 *   <dt>Lone last 'X'</dt>
 *     <dd>If the lone last letter is an 'X', append a 'Z'.</dd>
 *   <dt>Letters in same row</dt>
 *     <dd>If two letters appear in the same row of the grid when 
 *         <i>encrypting, </i> use the respective letters to their 
 *         <i>right </i> (and wrap around from the end of the row to the
 *         beginning). During <i>decrypting</i>, use the letters to the 
 *         <i>left </i>(and wrap from the beginning of the row to the 
 *         end).</dd>
 *   <dt>Letters in same column</dt>
 *     <dd>If two letters appear in the same column of the grid when
 *         <i>encrypting, </i> use the respective letters <i>beneath 
 *         </i>them (and wrap around from the bottom of the column to 
 *         the top). During <i>decrypting</i>, use the letters <i>above 
 *         </i>(and wrap from the top of the column to the bottom).</dd>
 *   <dt>Letters forming a box</dt>
 *     <dd>When two letters form a box in the grid, go <i>across rows 
 *         </i> to find the respective matching encrypted or decrypted 
 *         letter.</dd>
 *   </dl> 
 *
 * @author mrt
 */
public class PlayfairCipher implements Cryptosystem {
    
    // =================================================================
    // PRIVATE VARIABLES AND CONSTANTS
    // =================================================================

    // Encryption/decryption grid
    private char[][] grid = {
            "TAYLO".toCharArray(),
            "RFIZG".toCharArray(),
            "EDBCH".toCharArray(),
            "JKMNP".toCharArray(),
            "SUVWX".toCharArray()
            
    };
    
    // Dimensions of grid
    private final int ROWS = 5;
    private final int COLS = 5;
    
    // Character to use for splitting double-letters in plaintext
    private final char SPLIT_CHAR = 'X';
    // Character for splitting "XX" in the plaintext.
    private final char SPLIT_CHAR_ALT = 'Z';
    
    // Character for padding a lone last letter in plaintext
    private final char PAD_CHAR = 'X';
    // Character for padding a lone last 'X'
    private final char PAD_CHAR_ALT = 'Z';
    
    // The alphabet. Q is missing. Use "KW" for "QU" and "K" for "Q".
    final String ALPHABET = "ABCDEFGHIJKLMNOPRSTUVWXYZ";
    
    
    // =================================================================
    // PUBLIC METHODS AND CONSTRUCTORS
    // =================================================================
    
    /**
     * Default, no-arg constructor.
     */
    public PlayfairCipher() {
        // Nothing to do at this stage of the project.
    }

    /* (non-Javadoc)
     * @see Cryptosystem#encrypt(java.lang.String)
     */
    @Override
    public String encrypt(String plaintext) {
        plaintext = clean(plaintext);
        plaintext = removeWhitespace(plaintext);
        String result = "";
        String plaintext2 = "";    
        for (int i = 0; i < plaintext.length(); i += 2) {
        	char c1 = plaintext.charAt(i);
        	if (i == plaintext.length() - 1) {
        		plaintext2 += c1;
        		break;
        	}
        	char c2 = plaintext.charAt(i + 1);
        	plaintext2 += c1;
            if (c1 == c2) {
        		plaintext2 += SPLIT_CHAR;
        	}
        	if (c1 == 'X' && c2 == 'X') {
        		plaintext2 = plaintext2 + SPLIT_CHAR_ALT;
        	}
        	plaintext2 += c2;
        }
        if ((plaintext2.length() % 2) != 0) {
        	plaintext2 = plaintext2 + PAD_CHAR;
        }
        if (((plaintext2.length() % 2) != 0) && plaintext.charAt(plaintext.length() - 1) == 'X') {
        	plaintext2 = plaintext2 + PAD_CHAR_ALT;
        }
        
        // Loop through plaintext, each time getting a pair of
        // characters, and adding their encryption to the result.
        for (int i = 0; i < plaintext2.length(); i += 2) {
        	char c1 = plaintext2.charAt(i);
        	char c2 = plaintext2.charAt(i + 1);
        	
        	int c1row = findRowOf(c1);
        	int c1col = findColOf(c1);
        	int c2row = findRowOf(c2);
        	int c2col = findColOf(c2);
        	
        	char e1, e2;
        	
        	
        		
        	if (c1row == c2row) {
        		e1 = grid[ c1row ][ ((c1col + 6) % 5) ];
        		e2 = grid[ c1row ][ ((c2col + 6) % 5) ];
        	} else if (c1col == c2col) {
        		e1 = grid[ ((c1row + 6) % 5) ][ c1col ];
        		e2 = grid[ ((c2row + 6) % 5) ][ c2col ];
        	} else {
        		e1 = grid[ c1row ][ c2col ];
        		e2 = grid[ c2row ][ c1col ];
        	}
        	
        	result = result + e1 + e2;
        	
        }
        
        // When the result is built up, block it and return it.
        return block(result);        
    }
    
    /* (non-Javadoc)
     * @see Cryptosystem#decrypt(java.lang.String)
     */
    @Override
    public String decrypt(String ciphertext) {
        String result = "";
        ciphertext = removeWhitespace(ciphertext);
        
        // Loop through ciphertext, each time getting a pair of 
        // characters, and adding their decryptions to the result.
        for (int i = 0; i < ciphertext.length(); i += 2) {
            // Get the pair of characters; row and column of each
            char c1 = ciphertext.charAt(i);
            char c2 = ciphertext.charAt(i + 1);
            
            int c1row = findRowOf(c1);
            int c1col = findColOf(c1);
            int c2row = findRowOf(c2);
            int c2col = findColOf(c2);
            
            // Get decryption of each char. The expression 'c1row + (ROWS - 1)'
            //  ensures that the mod operator gives a positive result. If we
            //  just used 'c1row - 1' and c1row was 0, Java gives -1 (which is
            //  congruent to (ROWS - 1) modulo 5, but we want to use a value
            //  that's valid as an index).
            char d1, d2;                    // decrypted versions of c1, c2
            
            if (c1row == c2row) {                       // same row
                d1 = grid[ c1row ][ (c1col + 4) % COLS ];
                d2 = grid[ c1row ][ (c2col + 4) % COLS ];
            } else if (c1col == c2col) {                // same column
                d1 = grid[ (c1row + (ROWS - 1)) % ROWS ][ c1col ];
                d2 = grid[ (c2row + (ROWS - 1)) % ROWS ][ c1col ];
            } else {                                    // corners of box
                d1 = grid[ c1row ][ c2col ];
                d2 = grid[ c2row ][ c1col ];
            }
            
            // Append decryptions to result
            result = result + d1 + d2;
        }
        
        return result;
    }
    
    /* (non-Javadoc)
     * @see Cryptosystem#getSecretKey()
     */
    @Override
    public String getSecretKey() {
        // TODO Auto-generated method stub// TODO
        return null;
    }

    // =================================================================
    // PRIVATE ("HELPER") METHODS
    // =================================================================

    /**
     * Remove all whitespace from a string.
     * @param s the string needing whitespace removal
     * @return a copy of s but with all whitespace removed
     */
    protected String removeWhitespace(String s) {
        String result = "";
        for (int i = 0; i < s.length(); ++i) {
        	char c = s.charAt(i);
        	if (c == ' ') {
        	} else {
        		result += c;
        	}
        }
        return result;
    }

    /**
     * Insert spaces to form 5-character blocks, and newlines to form 
     * lines of 12 blocks.
     * @param s the string to put into blocks
     * @return the blocked string
     */
    protected String block(String s) {
        String result = "";
        int i;
        // Add as many full 5-char blocks as we can
        for (i = 0; i + 4 < s.length(); i += 5) {
            result += s.substring(i, i + 5);
            if ((i % 60) == 55) {
                result += '\n';
            } else {
                result += ' ';
            }
        }
        // Add the last chars
        result += s.substring(i);
        
        return result;
    }

    /**
     * Clean plaintext by uppercasing it and removing all non-
     *  alphabet characters.
     * @param plaintext the plaintext to clean
     * @return the plaintext in form ready to encrypt
     */
    protected String clean(String plaintext) {
        String result = "";
        plaintext = plaintext.toUpperCase();
        for (int i = 0; i < plaintext.length(); ++i) {
        	char c = plaintext.charAt(i);
        	if (c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F' || c == 'G' || c == 'H' || c == 'I' || c == 'J' || c == 'K' || c == 'L' || c == 'M' || c == 'N' || c == 'O' || c == 'P' || c == 'Q' || c == 'R' || c == 'S' || c == 'T' || c == 'U' || c == 'V' || c == 'W' || c == 'X' || c == 'Y' || c == 'Z') {
        		result = result + c;
        	}
        }
        return result;
    }
    
    /**
     * Find which row of the grid holds a certain character.
     * @param c the character to search for
     * @return the row that c appears in, or -1 if c is not in the grid
     */
    protected int findRowOf(char c) {
    	String row0 = new String(grid[0]);
    	String row1 = new String(grid[1]);
    	String row2 = new String(grid[2]);
    	String row3 = new String(grid[3]);
    	String row4 = new String(grid[4]);
    	int row0Index = row0.indexOf(c);
    	int row1Index = row1.indexOf(c);
    	int row2Index = row2.indexOf(c);
    	int row3Index = row3.indexOf(c);
    	int row4Index = row4.indexOf(c);
    	if (row0Index > -1) {
    		return 0;
    	} else if (row1Index > -1) {
    		return 1;
    	} else if (row2Index > -1) {
    		return 2;
    	} else if (row3Index > -1) {
    		return 3;
    	} else if (row4Index > -1){
    		return 4;
    	} else {
    		return -1;
    	}
    }
    
    /**
     * Find which column of the grid holds a certain character.
     * @param c the character to search for
     * @return the column that c appears in, or -1 if c is not in the grid
     */
    protected int findColOf(char c) {
    	String col0 = new String(grid[0]);
    	String col1 = new String(grid[1]);
    	String col2 = new String(grid[2]);
    	String col3 = new String(grid[3]);
    	String col4 = new String(grid[4]);
    	int col0Index = col0.indexOf(c);
    	int col1Index = col1.indexOf(c);
    	int col2Index = col2.indexOf(c);
    	int col3Index = col3.indexOf(c);
    	int col4Index = col4.indexOf(c);
    	if (col0Index > -1) {
    		return col0Index;
    	} else if (col1Index > -1) {
    		return col1Index;
    	} else if (col2Index > -1) {
    		return col2Index;
    	} else if (col3Index > -1) {
    		return col3Index;
    	} else if (col4Index > -1){
    		return col4Index;
    	} else {
    		return -1;
    	}
    }
    
}   //end class

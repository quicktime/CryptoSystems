package brendan;

/**
 * @author mrt
 * 
 */
public class CryptosystemDemo {

    /**
     * @param args
     *            unused
     */
    public static void main(String[] args) {
        String[] messages = {
                "HELLOWORLD",
                "THIS IS A TEST",
                "If you can read this, you decrypted it right!",
                "The playfair cipher was used by John F Kennedy to arrange\n"
                        + "rescue when he and th crew of PT Boat One Zero Nine"
                        + " were\nstranded in a contested area of the Pacific"
                        + " theatre\nduring World War II STOP The enemy"
                        + " intercepted the\nencrypted radio transmissions but"
                        + " did not subject them\nto cyrptanalysis STOP If"
                        + " they had then histry could have\nbeen much"
                        + " different as JFK later became the thrity\nfifth"
                        + " president of the United States STOP END"
        };

        String[] encrypts = {
                "EDOWO TXLZT HU",
                "OERVR VYAJT OS",
                "ZILTW DLKEJ FKOER VLTAK DHITJ ODBRY FZHPO S",
                "OEHJO YAIYF ZEGME DZSTU VUDBM IPTCP DUCJJ CBAAT TFFTP ZJEJT"
                + " DWCSE DPCDT\nKCOEE ZCSAG JOHYY ALPCR JELPZ MCSJE JTREL"
                + " KBDBF KLHLJ LJTRJ KFEJY TRAED\nKOBZI ZELED YAEJK AFZPZ"
                + " XLZTC UTFGV RVATJ OEDCJ BJIBJ LJEHD JODBO EHSCJ\nEZOMR"
                + " JEFFK GYREL KVJRV VRLPV ESABF CKTAU VEMDH OSOEB JATBL"
                + " GJAYK LOLVR\nUSTRG XZIOE BTDOE AEDPC RVREL BAXAC DOSBC"
                + " DCJKV HEBFG URDEJ JLTUK RNAYA\nJECDD LJBOE JREGR YAIZI"
                + " OEJGJ TFBCJ ATRAE DWKRY DBTRY AJTTR GXCJH U"

        };

        String[] decrypts = {
                "HELXLOWORLDX",
                "THISISATESTX",
                "IFYOUCANREADTHISYOUDECRYPTEDITRIGHTX",
                "THEPLAYFAIRCIPHERWASUSEDBYJOHNFKENNEDYTOARRANGERESCUEWHENHE"
                        + "ANDTHCREWOFPTBOATONEZERONINEWERESTRANDEDINA"
                        + "CONTESTEDAREAOFTHEPACIFICTHEATREDURINGWORLDWARIXI"
                        + "STOPTHEENEMYINTERCEPTEDTHEXENCRYPTEDRADIO"
                        + "TRANSMISSIONSBUTDIDNOTSUBJECTXTHEMTOCYRPT"
                        + "ANALYSISXSTOPIFTHEYHADTHENHISTRYCOULDHAVEBEENMUCH"
                        + "DIFXFERENTASJFKLATERBECAMETHETHRITYFIFTHPRESIDENT"
                        + "OFTHEUNITEDSTATESSTOPENDX"
        };

        PlayfairCipher pc = new PlayfairCipher();

        for (int i = 0; i < messages.length; ++i) {
            String msg = messages[i];
            String encrypt = encrypts[i];
            String decrypt = decrypts[i];
            String myencrypt = pc.encrypt(msg);
            String mydecrypt = pc.decrypt(encrypt);

            System.out.println("Message:\n" + "'" + msg + "'");
            System.out.println();
            System.out.println(">>> Your encrypt:\n" + "'" + myencrypt + "'");
            if (myencrypt.equals(encrypt)) {
                System.out.println(">>> Encryption correct! Good job!");
            } else {
                System.out.println(">>> Oops! Encrypt should be:\n" +
                        "'" + encrypt + "'");
            }
            System.out.println();

            System.out.println(">>> Your decrypt of actual encrypt: \n" +
                    "'" + mydecrypt + "'");
            if (mydecrypt.equals(decrypt)) {
                System.out.println(">>> Decryption correct! Excellent!");
             } else {
                System.out.println(">>> Oops! Decrypt should be:\n" +
                        "'" + decrypt + "'");
            }
            System.out.println("========================================");
        }

    }
}
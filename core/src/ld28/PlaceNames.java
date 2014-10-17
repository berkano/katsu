package ld28;

import katsu.Katsu;

import java.util.ArrayList;

/**
 * Created by shaun on 15/12/13.
 */
public class PlaceNames {

    public static ArrayList<String> placeNames = new ArrayList<String>();
    public static ArrayList<String> allNames = new ArrayList<String>();
    public static ArrayList<String> titles = new ArrayList<String>();
    public static ArrayList<String> shipTitles = new ArrayList<String>();

    public static String randomStringFromList(ArrayList<String> list) {
        return list.get(Katsu.random.nextInt(list.size()));
    }

    static {

        titles.add("Captain");
        titles.add("Commander");
        titles.add("Pirate");
        titles.add("Mister");
        titles.add("Ms");
        titles.add("Professor");
        titles.add("Doctor");
        titles.add("Trader");
        titles.add("Engineer");

        shipTitles.add("HMS");
        shipTitles.add("USS");
        shipTitles.add("Ranger");
        shipTitles.add("Shuttle");
        shipTitles.add("Transporter");

        String input = "Ahetetsun\n" +
                "Amanumu\n" +
                "Anemokin\n" +
                "Big\n" +
                "Blast\n" +
                "Blektor\n" +
                "Bluvn\n" +
                "Blycm\n" +
                "Brimd\n" +
                "Brokdoof\n" +
                "Brorbach\n" +
                "Brulfmunph\n" +
                "Brur\n" +
                "Cann\n" +
                "Chameng\n" +
                "Chikuro\n" +
                "Chrethrd\n" +
                "Chruk\n" +
                "Chrul\n" +
                "Chyron\n" +
                "Clachmos\n" +
                "Clusd\n" +
                "Coyphkin\n" +
                "Crashnt\n" +
                "Crelt\n" +
                "Crephum\n" +
                "Dadr\n" +
                "Dak\n" +
                "Doof\n" +
                "Drohgar\n" +
                "Droltyris\n" +
                "Drophck\n" +
                "Drotlor\n" +
                "Dumborg\n" +
                "Dunun\n" +
                "Erachi\n" +
                "Eshichi\n" +
                "Etorewa\n" +
                "Flultnumb\n" +
                "Forg\n" +
                "Frelchooz\n" +
                "Fukoma\n" +
                "Fuyo\n" +
                "Fystd\n" +
                "Gallndwar\n" +
                "Gedydra\n" +
                "Ghormhomph\n" +
                "Gork\n" +
                "Gorm\n" +
                "Grunk\n" +
                "Gunph\n" +
                "Hytrbur\n" +
                "Iroronu\n" +
                "Ishiko\n" +
                "Jydack\n" +
                "Kawanu\n" +
                "Kigdir\n" +
                "Kighkim\n" +
                "Kildd\n" +
                "Kite\n" +
                "Klelch\n" +
                "Kolnd\n" +
                "Koogklonk\n" +
                "Koph\n" +
                "Kuchi\n" +
                "Lertdar\n" +
                "Likth\n" +
                "Lis\n" +
                "Loln\n" +
                "Lomem\n" +
                "Lortckshy\n" +
                "Lundr\n" +
                "Montrr\n" +
                "Mub\n" +
                "Muring\n" +
                "Muyushi\n" +
                "Mymk\n" +
                "Nal\n" +
                "Natrd\n" +
                "Neph\n" +
                "Nirntar\n" +
                "Nitwem\n" +
                "Noltghum\n" +
                "Noss\n" +
                "Notck\n" +
                "Nunin\n" +
                "Nyvmor\n" +
                "Ochimami\n" +
                "Okukone\n" +
                "Omushiwa\n" +
                "Onenomi\n" +
                "Orata\n" +
                "Pebpelm\n" +
                "Phant\n" +
                "Phephkyer\n" +
                "Pih\n" +
                "Quyshest\n" +
                "Rafia\n" +
                "Ramon\n" +
                "Relwpol\n" +
                "Reqit\n" +
                "Rerrdech\n" +
                "Rev\n" +
                "Rhen\n" +
                "Rhesh\n" +
                "Rhintsay\n" +
                "Rhochash\n" +
                "Rhockmelm\n" +
                "Rhulr\n" +
                "Ripkryn\n" +
                "Rodth\n" +
                "Rohche\n" +
                "Rokoran\n" +
                "Rorrs\n" +
                "Ruc\n" +
                "Rudd\n" +
                "Ruketa\n" +
                "Rul\n" +
                "Ryghkmos\n" +
                "Sacz\n" +
                "Schys\n" +
                "Sendrr\n" +
                "Shecknn\n" +
                "Shil\n" +
                "Shimuyumu\n" +
                "Slav\n" +
                "Slickckust\n" +
                "Slilleld\n" +
                "Sloy\n" +
                "Sloys\n" +
                "Smelmther\n" +
                "Smort\n" +
                "Snenrves\n" +
                "Soltsh\n" +
                "Soqs\n" +
                "Stecr\n" +
                "Stessh\n" +
                "Stykqas\n" +
                "Suknnorm\n" +
                "Swanrine\n" +
                "Swild\n" +
                "Swumk\n" +
                "Sys\n" +
                "Thashshvor\n" +
                "Thog\n" +
                "Thomphuck\n" +
                "Thoofclot\n" +
                "Thritl\n" +
                "Throtler\n" +
                "Thultolph\n" +
                "Thyw\n" +
                "Tilan\n" +
                "Tildon\n" +
                "Tovtan\n" +
                "Trulzwar\n" +
                "Tysbur\n" +
                "Uchikemi\n" +
                "Umohotero\n" +
                "Vidskel\n" +
                "Wizrak\n" +
                "Wokblook\n" +
                "Yik\n" +
                "Yis\n" +
                "Yoke\n" +
                "Yosck\n" +
                "Zhodnn\n" +
                "Zhort";

        String[] words = input.split("\n");
        for (String w : words) {
            placeNames.add(w);
            allNames.add(w);
        }

    }

    public static String randomShipName() {
        return randomStringFromList(shipTitles) + " " + randomStringFromList(allNames);
    }

    public static String randomPersonName() {
        return randomStringFromList(titles) + " " + randomStringFromList(allNames);
    }

}

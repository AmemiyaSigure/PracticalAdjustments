package cx.rain.mc.forgemod.practicaladjustments.utility.enumerates;

public enum FurnaceType {
    Iron("iron"),
    Golden("golden"),
    Diamond("diamond"),
    SuperFurnace("super");

    String name = "";

    FurnaceType(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public FurnaceType fromName(String n) {
        switch (n) {
            case "iron":
                return Iron;
            case "golden":
                return Golden;
            case "diamond":
                return Diamond;
            case "super":
                return SuperFurnace;
            default:
                return null;
        }
    }
}

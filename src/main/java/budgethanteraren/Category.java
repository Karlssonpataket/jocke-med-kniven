package budgethanteraren;

public enum Category {
    HOME_AND_LIVING("Hem och Hushåll"),// The spaces are for printing it even. not very nice coding but it works
    AMUSEMENT("Nöje           "),
    TRAVEL("Resor          "),
    OTHER("Övrigt         "),
    SALARY("Lön            "),
    SAVINGS("Sparande       ");

    private final String displayName;


    Category(String displayName) {
        this.displayName = displayName;
    }


    public String getDisplayName() {
        return displayName;
    }
}


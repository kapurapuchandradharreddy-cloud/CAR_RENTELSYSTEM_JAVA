public enum Category {
    HATCHBACK,
    SEDAN,
    SUV,
    MUV,
    VAN,
    COUPE,
    CONVERTIBLE,
    LUXURY,
    OTHER;

    public static Category fromString(String s) {
        if (s == null) return OTHER;
        try {
            return Category.valueOf(s.trim().toUpperCase());
        } catch (Exception e) {
            return OTHER;
        }
    }
}


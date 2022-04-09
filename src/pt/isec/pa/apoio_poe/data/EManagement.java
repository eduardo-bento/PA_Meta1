package pt.isec.pa.apoio_poe.data;

public enum EManagement {
    STUDENTS,
    TEACHER,
    PROJECT_STAGE;

    public static EManagement fromInteger(int x) {
        return switch(x) {
            case 0 -> STUDENTS;
            case 1 -> TEACHER;
            case 2 -> PROJECT_STAGE;
            default -> null;
        };
    }

    public static String getTypes() {
        return """
                types:
                -0 :Students
                -1 :Teachers
                -2 :Project or stage
                """;
    }
}

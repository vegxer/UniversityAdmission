import university.University;

public class Main {
    public static void main(String[] args)
    {
        try {
            University vyatsu = new University("facultiesFiles/faculties.json",
                    "applicantsFiles/applicants.json");
            vyatsu.notifyObservers();
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }
}

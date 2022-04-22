package pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals;

public class Proposal {
    protected String id;
    protected String title;
    protected long student;
    private int _hasCandidacy;

    protected Proposal(String id, String title,long studentID) {
        this.id = id;
        this.title = title;
        this.student = studentID;
    }

    public static Proposal getFakeProposal(String id){
        return new Proposal(id,"---",9999999);
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int get_hasCandidacy() {
        return _hasCandidacy;
    }

    public void addCandidacy() {
        _hasCandidacy++;
    }

    public void subCandidacy() {
        _hasCandidacy--;
    }

   /* public static List<Object> readFile(String filePath){
        List<Object> data = new ArrayList<>();
        List<Object> proposals = new ArrayList<>();

        try(Scanner input = new Scanner(new File(filePath))) {
            input.useDelimiter(",\\s*|\r\n|\n");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                String type = input.next();
                data.add(input.next());
                switch (type){
                    case "T1"-> {
                        String branch = input.next();
                        String title = input.next();
                        String entity = input.next();
                        data.add(title);
                        data.add(entity);
                        data.add(branch);
                        data.add(-1L);
                        proposals.add(EDataStructure.INTER_SHIP.factory(data));
                    }
                    case "T2"-> {
                        String branch = input.next();
                        String title = input.next();
                        String email = input.next();

                        data.add(title);
                        data.add(email);
                        data.add(branch);
                        try {
                            int student = input.nextInt();
                            data.add(student);
                        } catch (InputMismatchException e){
                            data.add(-1);
                        }
                        proposals.add(EDataStructure.PROJECT.factory(data));
                    }
                    case "T3"-> {
                        data.add(input.next());
                        data.add(input.nextLong());;
                        proposals.add(EDataStructure.SELF_PROPOSAL.factory(data));
                    }
                }
                data.clear();
            }
        }  catch (Exception e){
            Log.getInstance().addMessage("The file does not exist");
        }
        return proposals;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proposal proposal = (Proposal) o;

        return id != null ? id.equals(proposal.id) : proposal.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

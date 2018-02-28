package tikape.tikaperyhmatyo.domain;

public class Ingredient {

    private Integer id;
    private String name;
    private String numberofuses;

    public Ingredient(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.numberofuses = "used in 0 smoothies";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfUses(Integer numberOfUses) {
        this.numberofuses = "used in " + numberOfUses.toString() + " smoothies";
    }

    public String getNumberOfUses() {
        return this.numberofuses;
    }

}

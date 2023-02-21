package _project.display;

public class Display {
    private Section input;
    private Section output;

    public Display() {
        //construct the different sections within the display
        this.input = new InputSection();
        this.output = new OutputSection();
    }
}

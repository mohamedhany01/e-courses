package byow.Core.gui;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class WindowBuilder {
    private Window menu;
    public WindowBuilder(Window menu) {
        this.menu = menu;
    }

    public WindowBuilder setTitle(String title, int marginTop, int fontSize) {
        final int x = this.menu.getWidth();

        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, fontSize));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(x/2, marginTop, title);

        return this;
    }

    public WindowBuilder setOption(String option, int marginTop, int fontSize) {
        final int x = this.menu.getWidth();

        StdDraw.setFont(new Font("Monaco", Font.PLAIN, fontSize));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(x/2, marginTop, option);

        return this;
    }

    public Window getMenu() {
        return this.menu;
    }

    public void render() {
        StdDraw.show();
    }

}

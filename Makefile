MF = /tmp/8queensManifest

8QUEENS = eightqueens.jar
SRCDIR = eightqueens

JFLAGS = -g
JAVAC = javac -cp ./$(SRCDIR):${CLASSPATH}

.SUFFIXES: .java .class
.java.class:
	$(JAVAC) $(JFLAGS) $<

_8QUEENS_SRC = EightQueensApplet.java \
	EightQueensFrame.java \
	EightQueensPanel.java \
	EightQueensGui.java \
	Square.java \
	SquareView.java \
	SquareViewMouseHandler.java

8QUEENS_SRC = $(_8QUEENS_SRC:%=$(SRCDIR)/%)

8QUEENS_CLASSES = $(8QUEENS_SRC:.java=.class)

$(8QUEENS):	$(8QUEENS_SRC) $(8QUEENS_CLASSES)
	rm -f $(MF)
	echo "Main-Class: $(SRCDIR)/EightQueensFrame" > $(MF)
	jar cmf $(MF) $@ $(SRCDIR)/*.class
	rm -f $(MF)

clean:
	rm -f $(8QUEENS) $(SRCDIR)/*.class

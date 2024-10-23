JAVAC = javac
JFLAGS = -g
SRCDIR = src
BINDIR = bin
DOCDIR = doc

.SUFFIXES: .java .class

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR) -cp $(BINDIR) $<

CLASSES= $(BINDIR)/Generic.class $(BINDIR)/BinaryTreeNode.class $(BINDIR)/BinaryTree.class $(BINDIR)/AVLTree.class $(BINDIR)/GenericsKbAVLAppGUI.class\

default: $(CLASSES)

clean:
	rm $(BINDIR)/*.class

runAVL:
	java -cp $(BINDIR) GenericsKbAVLAppGUI > result.txt

javadoc:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

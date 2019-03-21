#include "Gui.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
	Arbore abc;
	Colectie col{ abc };
	Gui app{ col };
	app.show();
	return a.exec();
}

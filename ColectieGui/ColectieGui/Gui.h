#pragma once
#include <QtWidgets\qwidget.h>
#include <qpushbutton.h>
#include <qtablewidget.h>
#include <qlineedit.h>
#include <qlabel.h>
#include <qboxlayout.h>
#include <qmessagebox.h>
#include <qformlayout.h>

#include "Colectie.h"


class Gui :public QWidget {
private:
	Colectie & col;
	QTableWidget * table;

	QPushButton* btnAdd;
	QPushButton* btnRem;
	QPushButton* btnMod;
	QPushButton* btnPret;

	void addGui();
	void remGui();
	void modGui();
	void pretMediu();


	void initComps();
	void initConnections();
	void reloadList();

	QLabel* pretMediuLbl;
	QLineEdit* pretMediuTxt;
	QLineEdit* stocTxt;
	QLineEdit* numeTxt;
	QLineEdit* pretTxt;
	QLineEdit* producatorTxt;

public:
	Gui(Colectie& col) :col{ col } {
		initComps();
		initConnections();
		reloadList();
	}
};
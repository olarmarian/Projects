#include "Gui.h"

void Gui::initComps() {
	QHBoxLayout* layout = new QHBoxLayout;
	setLayout(layout);
	table = new QTableWidget(col.dim()+100, 4);
	table->setBaseSize(width(), height());
	table->setMaximumSize(table->columnWidth(1)*4, height());
	QWidget* tableWid=new QWidget;
	QVBoxLayout* tableLayout = new QVBoxLayout;
	tableLayout->addWidget(new QLabel("Stocul de medicamente"));
	
	tableLayout->addWidget(table);
	
	tableWid->setLayout(tableLayout);
	layout->addWidget(tableWid);

	QWidget* crudWid = new QWidget;
	QFormLayout* crudLayout = new QFormLayout;
	btnAdd = new QPushButton("Adauga");
	btnRem = new QPushButton("Sterge");
	btnMod = new QPushButton("Modifica");
	numeTxt = new QLineEdit;
	pretTxt = new QLineEdit;
	stocTxt = new QLineEdit;
	pretMediuTxt = new QLineEdit;
	producatorTxt = new QLineEdit;
	crudLayout->addRow(new QLabel("Nume: "), numeTxt);
	crudLayout->addRow(new QLabel("Pret: "), pretTxt);
	crudLayout->addRow(new QLabel("Producator: "), producatorTxt);
	crudLayout->addRow(new QLabel("Stoc: "), stocTxt);
	crudLayout->addRow(btnAdd);
	crudLayout->addRow(btnRem);
	crudLayout->addRow(btnMod);
	pretMediuLbl = new QLabel();
	crudLayout->addRow(new QLabel("Pret:"),pretMediuLbl);
	crudLayout->addRow(pretMediuTxt);
	btnPret = new QPushButton("pret");
	crudLayout->addRow(btnPret);
	crudWid->setLayout(crudLayout);

	layout->addWidget(crudWid);

}

void Gui::initConnections() {
	QObject::connect(btnAdd, &QPushButton::clicked, this, &Gui::addGui);
	QObject::connect(btnRem, &QPushButton::clicked, this, &Gui::remGui);
	QObject::connect(btnMod, &QPushButton::clicked, this, &Gui::modGui);
	QObject::connect(btnPret, &QPushButton::clicked, this, &Gui::pretMediu);
}

void Gui::reloadList() {
	table->clear();
	Iterator it=col.iterator();
	it.prim();
	int rand = 0;
	while (it.valid()) {
		QTableWidgetItem* nume = new QTableWidgetItem(QString::fromStdString(it.element().getNume()));
		QTableWidgetItem* pret = new QTableWidgetItem(QString::number(it.element().getPret()));
		QTableWidgetItem* prod = new QTableWidgetItem(QString::fromStdString(it.element().getProducator()));
		QTableWidgetItem* stoc = new QTableWidgetItem(QString::number(it.element().getStoc()));
		table->setItem(rand, 0, nume);
		table->setItem(rand, 1, pret);
		table->setItem(rand, 2, prod);
		table->setItem(rand, 3, stoc);
		it.urmator();
		rand++;
	}
}


void Gui::addGui() {
	try
	{
		std::string nume;
		std::string producator;
		int pret;
		int stoc;

		nume = numeTxt->text().toStdString();
		producator = producatorTxt->text().toStdString();
		pret = pretTxt->text().toInt();
		stoc = stocTxt->text().toInt();
		TElement t{ nume, pret ,producator ,stoc};
		col.adauga(t);
		reloadList();
		numeTxt->clear();
		stocTxt->clear();
		pretTxt->clear();
		producatorTxt->clear();
	}
	catch (const std::exception& ex){
		QMessageBox::warning(this, "Warning", "Adaugare");
	}
}
void Gui::remGui() {
	try
	{
		std::string nume;
		nume = numeTxt->text().toStdString();
		TElement t{ nume, 3,"a",3};
		col.sterge(t);
		reloadList();
		numeTxt->clear();
		stocTxt->clear();
		pretTxt->clear();
		producatorTxt->clear();
	}
	catch (const std::exception& ex) {
		std::cout << ex.what();
	}
	catch (ColectieException& ex) {
		QMessageBox::warning(this, "Warninig", QString::fromStdString(ex.getMessage()));
	}
}

void Gui::modGui() {
	try
	{
		std::string nume;
		std::string producator;
		int pret;
		int stoc;
		nume = numeTxt->text().toStdString();
		producator = producatorTxt->text().toStdString();
		pret = pretTxt->text().toInt();
		stoc = stocTxt->text().toInt();
		TElement t{ nume, pret ,producator,stoc };
		col.modifica(t);
		reloadList();
		numeTxt->clear();
		pretTxt->clear();
		stocTxt->clear();
		producatorTxt->clear();
	}
	catch (const std::exception& ex) {
		QMessageBox::warning(this, "Warning", "Eroare");
	}
	catch (const ColectieException& ex) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(ex.getMessage()));
	}
}
void Gui::pretMediu() {
	try
	{
		std::string producator;
		producator= pretMediuTxt->text().toStdString();
		Iterator it=col.iterator();
		int pret = 0;
		int nr_el = 0;
		while (it.valid()) {
			if (it.element().getProducator() == producator) {
				pret += it.element().getPret();
				nr_el++;
			}
			it.urmator();
		}
		pretMediuLbl->setText(QString::number(pret/nr_el));
		producatorTxt->clear();
	}
	catch (const std::exception& ex) {
		QMessageBox::warning(this, "Warning", "Eroare");
	}
	catch (const ColectieException& ex) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(ex.getMessage()));
	}
}
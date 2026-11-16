package org.route.model;

public enum Governorate {
    Cairo(Zone.GREATER_CAIRO), Giza(Zone.GREATER_CAIRO), Qalyubia(Zone.GREATER_CAIRO),

    Alexandria(Zone.ALEX_DELTA), Beheira(Zone.ALEX_DELTA), Kafr_El_Sheikh(Zone.ALEX_DELTA),
    Dakahlia(Zone.ALEX_DELTA), Damietta(Zone.ALEX_DELTA), Gharbia(Zone.ALEX_DELTA),
    Monufia(Zone.ALEX_DELTA), Sharqia(Zone.ALEX_DELTA),

    Port_Said(Zone.CANAL), Ismailia(Zone.CANAL), Suez (Zone.CANAL),

    Faiyum(Zone.UPPER_EGYPT), Beni_Suef(Zone.UPPER_EGYPT), Minya(Zone.UPPER_EGYPT), Asyut(Zone.UPPER_EGYPT),
    Sohag(Zone.UPPER_EGYPT), Qena(Zone.UPPER_EGYPT), Luxor(Zone.UPPER_EGYPT), Aswan(Zone.UPPER_EGYPT),

    Matrouh(Zone.REMOTE), Northsinai(Zone.REMOTE), Southsinai(Zone.REMOTE),
    Redsea(Zone.REMOTE), Newvalley(Zone.REMOTE);

    private Zone zone;

    Governorate(Zone zone) {
        this.zone = zone;
    }

    public Zone getZone() {
        return zone;
    }
}

import { FilmSerien } from "../media/media";
import { Benutzerprofile } from "../user/benutzer";
export interface Bewertung{
    id: number;
    benutzerprofil:Benutzerprofile;
    filmSerien :FilmSerien;
    kommentar: string;
    bewertungswert: number
}

import { Category } from "./Category";

export interface FilmSerien{
    id: number;
    urltrailer: string;
    titel: string;
    beschreibung: string;
    veroeffentlichungsjahr: String;
    imgSrc:string;
    categories: Category[];
}

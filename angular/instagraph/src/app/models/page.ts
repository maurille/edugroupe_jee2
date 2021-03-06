export class Page<T> {
    constructor(public content: T[],
                public number: number,
                public numberOfElements: number,
                public size: number,
                public totalElements: number,
                public totalPages: number,
                public first: boolean,
                public last: boolean,
                public sort: any) {}

                // cela nous evitera de faire new dans notre code a chaque fois qu'on aura besoin d'une nouvelle page
                public static emptyPage<T>() : Page<T>{

                    let p = new Page<T>([], 0,0,12,0,1,true, true, {direction : "ASC", property: "id"});

                    return p;
                }

}
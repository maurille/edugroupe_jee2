export class Tache{
    constructor(public id: number,
                public libelle: string,
                public description: string,
                public priorite: number,
                public dateCreation: Date,
            public dateLimite: Date,
                public contexte: string,
                public termine: boolean ){}
}
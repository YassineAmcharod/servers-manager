import { Server } from "./server";

export interface CustomResponse {
    timeStamp: Date;
    statusCode: number;
    status: string;
    reason: string;
    message: string;
    developerMessage: string;
    data: {servers?: Server[], server?: Server };  // ? === optionnal; donct l'un des deux
}
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Status } from '../enum/status.enum';
import { CustomResponse } from '../interface/custom-response';
import { Server } from '../interface/server';

@Injectable({ providedIn: 'root' })
export class ServerService {

  private readonly apiUrl = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  servers$ = <Observable<CustomResponse>>
  this.http.get<CustomResponse>(`${this.apiUrl}/server/list`)
  .pipe(
    tap(console.log),
    catchError(this.handeleError)
  );

  save$ = (server: Server) => <Observable<CustomResponse>>
  this.http.post<CustomResponse>(`${this.apiUrl}/server/save`, server)
  .pipe(
    tap(console.log),
    catchError(this.handeleError)
  );

  ping$ = (ipAddress: string) => <Observable<CustomResponse>>
  this.http.get<CustomResponse>(`${this.apiUrl}/server/ping/${ipAddress}`)
  .pipe(
    tap(console.log),
    catchError(this.handeleError)
  );

  delete$ = (serverId: number) => <Observable<CustomResponse>>
  this.http.delete<CustomResponse>(`${this.apiUrl}/server/delete/${serverId}`)
  .pipe(
    tap(console.log),
    catchError(this.handeleError)
  );

  filter$ = (status: Status, response: CustomResponse) => <Observable<CustomResponse>>
  new Observable<CustomResponse>(
    subscriber => {
      console.log(response);
      subscriber.next(
        status === Status.ALL ? { ...response, message:`Servers filtred by ${status} status`} : 
        {
          ...response,
          message: response.data.servers
          .filter(server => server.status === status).length > 0 ? `Servers filtred by ${status.replace('_', ' ')} status` 
          : `No Servers of status ${status.replace('_', ' ')} found`,
          data : {servers : response.data.servers.filter(server => server.status === status)}
        }
      );
      subscriber.complete();
    }
  )
  .pipe(
    tap(console.log),
    catchError(this.handeleError)
  );

  private handeleError(error: HttpErrorResponse): Observable<never> {
    console.log(error)
    return throwError('an Error occured - error code: ' + error.status);
  }
}

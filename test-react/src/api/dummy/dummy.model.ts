export enum DummySourceSystem {
    INTERNAL = "INTERNAL",
    EXTERNAL = "EXTERNAL"
}

/**
 * Dummy description
 */
export interface Dummy {
    id: number,
    name: string,
    sourceSystem: DummySourceSystem,
    furtherInformation: string,
    internalInformation?: string,
    externalInformation?: string
}